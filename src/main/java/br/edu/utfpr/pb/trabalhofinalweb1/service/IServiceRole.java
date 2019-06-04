package br.edu.utfpr.pb.trabalhofinalweb1.service;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Role;

public interface IServiceRole extends IServiceCrud<Role, Integer> {
    Role findAdminRole();
}
