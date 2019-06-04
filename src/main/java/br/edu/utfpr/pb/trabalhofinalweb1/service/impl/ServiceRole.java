package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Role;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryRole;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceRole extends ServiceCrud<Role, Integer>
        implements IServiceRole {

    @Autowired
    private RepositoryRole repositoryRole;

    @Override
    protected JpaRepository<Role, Integer> getRepository() {
        return repositoryRole;
    }

    @Override
    public Role findAdminRole() {
        return repositoryRole.findByName("ROLE_ADMIN");
    }
}
