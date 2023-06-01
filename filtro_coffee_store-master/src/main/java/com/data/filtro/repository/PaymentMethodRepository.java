package com.data.filtro.repository;

import com.data.filtro.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    @Query("select pm from PaymentMethod pm where pm.id=:id")
    PaymentMethod findPaymentMethodById(@Param("id") int id);

}
