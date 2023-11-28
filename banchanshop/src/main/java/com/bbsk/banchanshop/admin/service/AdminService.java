package com.bbsk.banchanshop.admin.service;

import com.bbsk.banchanshop.admin.dto.ResponseDashboardDto;
import com.bbsk.banchanshop.admin.dto.ResponseOrdersDto;
import com.bbsk.banchanshop.banchan.repository.BanchanRepository;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import com.bbsk.banchanshop.order.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final OrdersRepository ordersRepository;
    private final BanchanRepository banchanRepository;

    public ResponseDashboardDto getFindDashboardInfo() {

        ResponseDashboardDto dto = new ResponseDashboardDto();
        dto.updateTodayOrderCount(ordersRepository.findOrderCountByToday());
        dto.updateRecently7DaysOrderCount(ordersRepository.findOrderCountBy7Day());
        dto.updateRecently6MonthsOrderCount(ordersRepository.findOrderCountBy6Months());
        dto.updateBanchanInfoDtoList(banchanRepository.findAll());

        return dto;
    }

    public List<ResponseOrdersDto> findAllOrders() {
        List<ResponseOrdersDto> orders = new ArrayList<>();

        for (OrdersEntity order : ordersRepository.findAll()) {
            orders.add(new ResponseOrdersDto().updateOrders(order));
        }

        return orders;
    }
}
