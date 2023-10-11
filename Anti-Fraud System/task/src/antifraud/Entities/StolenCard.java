package antifraud.Entities;

import antifraud.Annotations.ValidCardNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stolen_cards")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StolenCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id")
    private long id;

    @NotEmpty
    @NotNull
    @ValidCardNumber
    @Column(name ="number")
    private String number;
}
