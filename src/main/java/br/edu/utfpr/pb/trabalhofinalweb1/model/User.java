package br.edu.utfpr.pb.trabalhofinalweb1.model;

import br.edu.utfpr.pb.trabalhofinalweb1.converter.BooleanConverter;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "`user`")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;
    private static final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(10);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120, nullable = false)
    @NotBlank(message = "Nome de preenchimento obrigatório")
    private String name;

    @Column(length = 120, nullable = false)
    @NotBlank(message = "Email de preenchimento obrigatório")
    private String email;

    @Column(length = 120, nullable = false)
    @NotBlank(message = "Nome do usuário obrigatório")
    private String username;

    @Convert(converter = BooleanConverter.class)
    @Column(columnDefinition = "char(1) default 'V'")
    private boolean enabled = true;

    @Column(length = 512)
    private String password;

    @Column(length = 255, nullable = true)
    private String imageUrl;

    @NotBlank(message = "Preencha o cpf!")
    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.addAll(roles);
        return list;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getEncondedPassword() {
        return bCrypt.encode(password);
    }
}
