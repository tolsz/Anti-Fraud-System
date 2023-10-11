package antifraud.Services;

import antifraud.Annotations.ValidCardNumber;
import antifraud.Annotations.ValidIP;
import antifraud.Constraints.FormattingConstraints;
import antifraud.Entities.StolenCard;
import antifraud.Entities.SuspiciousIP;
import antifraud.Entities.Transaction;
import antifraud.Repositories.StolenCardRepository;
import antifraud.Repositories.SuspiciousIPRepository;
import antifraud.Repositories.TransactionRepository;
import antifraud.Requests.TransactionFeedbackRequest;
import antifraud.Requests.TransactionRequest;
import antifraud.Responses.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class AntiFraudService {

    private final TransactionRepository transactionRepository;
    private final SuspiciousIPRepository suspiciousIPRepository;
    private final StolenCardRepository stolenCardRepository;

    @Autowired
    TransactionValidationService transactionValidationService;

    @Autowired
    LimitHandlerService limitHandlerService;

    @Autowired
    public AntiFraudService(TransactionRepository transactionRepository,
                            SuspiciousIPRepository suspiciousIPRepository,
                            StolenCardRepository stolenCardRepository) {
        this.transactionRepository = transactionRepository;
        this.suspiciousIPRepository = suspiciousIPRepository;
        this.stolenCardRepository = stolenCardRepository;
    }

    public ResponseEntity<TransactionExecutedResponse> processTransaction(TransactionRequest request) {
        transactionValidationService.validateTransaction(request);

        return transactionValidationService.getValidatedTransaction();
    }

    public ResponseEntity<List<TransactionInfoResponse>> getTransactions() {
        List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();

        return ResponseEntity.ok(transactions
                .stream()
                .map(TransactionInfoResponse::new)
                .collect(Collectors.toList()));
    }

    public ResponseEntity<List<TransactionInfoResponse>> getTransactionsByNumber(String number) {
        if (!FormattingConstraints.isValidCardNumber(number)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (!transactionRepository.existsByNumber(number)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Transaction> transactions = transactionRepository.findByNumber(number);

        return ResponseEntity.ok(transactions
                .stream()
                .map(TransactionInfoResponse::new)
                .collect(Collectors.toList()));
    }

    public ResponseEntity<TransactionInfoResponse> addTransactionFeedback(TransactionFeedbackRequest request) {
        if (!FormattingConstraints.isValidFeedback(request.getFeedback())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (!transactionRepository.existsById(request.getTransactionId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Transaction transaction = transactionRepository.findById(request.getTransactionId());

        if (FormattingConstraints.isValidFeedback(transaction.getFeedback())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if (transaction.getResult().equals(request.getFeedback())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        transaction.setFeedback(request.getFeedback());
        transactionRepository.save(transaction);

        limitHandlerService.handleLimits(transaction, request.getFeedback());

        return ResponseEntity.ok(new TransactionInfoResponse(transaction));
    }

    public ResponseEntity<List<SuspiciousIPResponse>> getSuspiciousIps() {
        List<SuspiciousIP> suspiciousIPs = (List<SuspiciousIP>) suspiciousIPRepository.findAll();

        return ResponseEntity.ok(suspiciousIPs
                .stream()
                .map(SuspiciousIPResponse::new)
                .collect(Collectors.toList()));
    }

    @Transactional
    public ResponseEntity<SuspiciousIpDeletedResponse> deleteSuspiciousIp(@ValidIP String ip) {
        if (!suspiciousIPRepository.existsByIp(ip)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        suspiciousIPRepository.deleteByIp(ip);

        return ResponseEntity.ok(new SuspiciousIpDeletedResponse(ip));
    }

    public ResponseEntity<SuspiciousIPResponse> addSuspiciousIp(SuspiciousIP suspiciousIP, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (suspiciousIPRepository.existsByIp(suspiciousIP.getIp())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        suspiciousIPRepository.save(suspiciousIP);

        return ResponseEntity.ok(new SuspiciousIPResponse(suspiciousIP));
    }

    public ResponseEntity<List<StolenCardResponse>> getStolenCards() {
        List<StolenCard> stolenCards = (List<StolenCard>) stolenCardRepository.findAll();

        return ResponseEntity.ok(stolenCards
                .stream()
                .map(StolenCardResponse::new)
                .collect(Collectors.toList()));
    }

    public ResponseEntity<StolenCardResponse> addStolenCard(StolenCard stolenCard, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (stolenCardRepository.existsByNumber(stolenCard.getNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        stolenCardRepository.save(stolenCard);

        return ResponseEntity.ok(new StolenCardResponse(stolenCard));
    }

    @Transactional
    public ResponseEntity<StolenCardDeletedResponse> deleteStolenCard(@ValidCardNumber String number) {
        if (!stolenCardRepository.existsByNumber(number)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        stolenCardRepository.deleteByNumber(number);

        return ResponseEntity.ok(new StolenCardDeletedResponse(number));
    }
}
