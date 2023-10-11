package antifraud.Services;

import antifraud.Constants.TransactionStatus;
import antifraud.Entities.Transaction;
import antifraud.Entities.TransactionLimit;
import antifraud.Repositories.TransactionLimitsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class LimitHandlerService {

    private TransactionLimitsRepository transactionLimitsRepository;

    @Autowired
    public LimitHandlerService(TransactionLimitsRepository transactionLimitsRepository) {
        this.transactionLimitsRepository = transactionLimitsRepository;
    }

    public void handleLimits(Transaction transaction, String feedback) {
        if (!transactionLimitsRepository.findAll().iterator().hasNext()) {
            initializeLimits();
        }

        if (transaction.getResult().equals(TransactionStatus.ALLOWED) && feedback.equals(TransactionStatus.MANUAL)) {
            decreaseMaxAllowedLimit(transaction.getAmount());
            return;
        }

        if (transaction.getResult().equals(TransactionStatus.ALLOWED) && feedback.equals(TransactionStatus.PROHIBITED)) {
            decreaseMaxAllowedLimit(transaction.getAmount());
            decreaseMaxManualLimit(transaction.getAmount());
            return;
        }

        if (transaction.getResult().equals(TransactionStatus.MANUAL) && feedback.equals(TransactionStatus.ALLOWED)) {
            increaseMaxAllowedLimit(transaction.getAmount());
            return;
        }

        if (transaction.getResult().equals(TransactionStatus.MANUAL) && feedback.equals(TransactionStatus.PROHIBITED)) {
            decreaseMaxManualLimit(transaction.getAmount());
            return;
        }

        if (transaction.getResult().equals(TransactionStatus.PROHIBITED) && feedback.equals(TransactionStatus.ALLOWED)) {
            increaseMaxAllowedLimit(transaction.getAmount());
            increaseMaxManualLimit(transaction.getAmount());
            return;
        }

        increaseMaxManualLimit(transaction.getAmount());
    }

    private void initializeLimits() {
        TransactionLimit transactionLimitAllowed = new TransactionLimit();
        TransactionLimit transactionLimitManual = new TransactionLimit();

        transactionLimitAllowed.setType("MAX_ALLOWED");
        transactionLimitManual.setType("MAX_MANUAL");

        transactionLimitAllowed.setMaxValue(200);
        transactionLimitManual.setMaxValue(1500);

        transactionLimitsRepository.saveAll(List.of(transactionLimitAllowed, transactionLimitManual));
    }

    private void increaseMaxAllowedLimit(long transactionValue) {
        TransactionLimit transactionLimit = transactionLimitsRepository.findByType("MAX_ALLOWED");
        long currentLimit = transactionLimit.getMaxValue();
        long newLimit = (long) Math.ceil(0.8 * currentLimit + 0.2 * transactionValue);

        transactionLimit.setMaxValue(newLimit);

        transactionLimitsRepository.save(transactionLimit);
    }

    private void increaseMaxManualLimit(long transactionValue) {
        TransactionLimit transactionLimit = transactionLimitsRepository.findByType("MAX_MANUAL");
        long currentLimit = transactionLimit.getMaxValue();
        long newLimit = (long) Math.ceil(0.8 * currentLimit + 0.2 * transactionValue);

        transactionLimit.setMaxValue(newLimit);

        transactionLimitsRepository.save(transactionLimit);
    }

    private void decreaseMaxAllowedLimit(long transactionValue) {
        TransactionLimit transactionLimit = transactionLimitsRepository.findByType("MAX_ALLOWED");
        long currentLimit = transactionLimit.getMaxValue();
        long newLimit = (long) Math.ceil(0.8 * currentLimit - 0.2 * transactionValue);

        transactionLimit.setMaxValue(newLimit);

        transactionLimitsRepository.save(transactionLimit);
    }

    private void decreaseMaxManualLimit(long transactionValue) {
        TransactionLimit transactionLimit = transactionLimitsRepository.findByType("MAX_MANUAL");
        long currentLimit = transactionLimit.getMaxValue();
        long newLimit = (long) Math.ceil(0.8 * currentLimit - 0.2 * transactionValue);

        transactionLimit.setMaxValue(newLimit);

        transactionLimitsRepository.save(transactionLimit);
    }
}
