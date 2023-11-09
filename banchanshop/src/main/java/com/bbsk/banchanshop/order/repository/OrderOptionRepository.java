package com.bbsk.banchanshop.order.repository;

import com.bbsk.banchanshop.order.entity.OrderOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOptionRepository extends JpaRepository<OrderOptionEntity, Long> {
}
