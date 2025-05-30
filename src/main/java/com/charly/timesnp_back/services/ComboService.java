package com.charly.timesnp_back.services;

import com.charly.timesnp_back.dtos.ComboDTO;
import com.charly.timesnp_back.models.Combo;
import com.charly.timesnp_back.models.Rol;

import java.util.List;

public interface ComboService {
    public List<Combo> getCombos();
    public List<Combo> getCombosByProveedor();
    public List<Rol> getRoles();
    public Combo createCombo(ComboDTO comboDTO);
    public void deleteCombo(String id);
}
