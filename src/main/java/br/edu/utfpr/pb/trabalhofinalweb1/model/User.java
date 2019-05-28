package br.edu.utfpr.pb.trabalhofinalweb1.model;

import br.edu.utfpr.pb.trabalhofinalweb1.converter.BooleanConverter;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private boolean enabled;

    @Column(length = 512, nullable = false)
    @NotBlank(message = "Senha de preenchimento obrigatório")
    private String password;

    @Column(length = 255, nullable = true)
    private String imageUrl;

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

}
