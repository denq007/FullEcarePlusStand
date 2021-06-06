package com.t_systems.ecare.eCare.basket;

import com.t_systems.ecare.eCare.DTO.ContractDTO;

import java.util.Optional;

public interface Basket {
    BasketImpl createBasket(ContractDTO dto);

    Optional<String> update(BasketImpl currentBasket, BasketImpl basket);

    Optional<String> checkOptions();

    void clearAll();
}
