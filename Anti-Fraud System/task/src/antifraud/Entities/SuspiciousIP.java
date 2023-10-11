package antifraud.Entities;

import antifraud.Annotations.ValidIP;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suspicious_IPs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuspiciousIP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id")
    private long id;

    @NotEmpty
    @NotNull
    @ValidIP
    @Column(name = "ip")
    private String ip;
}
