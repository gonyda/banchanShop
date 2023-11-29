package com.bbsk.banchanshop.admin.service;

import com.bbsk.banchanshop.admin.dto.*;
import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;
import com.bbsk.banchanshop.banchan.repository.BanchanRepository;
import com.bbsk.banchanshop.banchan.service.BanchanService;
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
    private final BanchanService banchanService;

    /**
     * 통계성 대시보드
     * @return
     */
    public ResponseDashboardDto getFindDashboardInfo() {

        ResponseDashboardDto dto = new ResponseDashboardDto();
        dto.updateTodayOrderCount(ordersRepository.findOrderCountByToday());
        dto.updateRecently7DaysOrderCount(ordersRepository.findOrderCountBy7Day());
        dto.updateRecently6MonthsOrderCount(ordersRepository.findOrderCountBy6Months());
        dto.updateBanchanInfoDtoList(banchanRepository.findAll());

        return dto;
    }

    /**
     * 주문현황
     * @return
     */
    public List<ResponseOrdersDto> findAllOrders(Long orderId, String userId, String orderDate) {
        List<ResponseOrdersDto> orders = new ArrayList<>();

        for (OrdersEntity order : ordersRepository.findAllOrders(orderId, userId, orderDate)) {
            orders.add(new ResponseOrdersDto().updateOrders(order));
        }

        return orders;
    }

    /**
     * 반찬관리 - 반찬리스트
     * @return
     */
    public List<ResponseBanchanDto> findAllBanchan() {
        List<ResponseBanchanDto> banchanList = new ArrayList<>();

        for (BanchanEntity e : banchanRepository.findAll()) {
            banchanList.add(new ResponseBanchanDto(e));
        }

        return banchanList;
    }

    /**
     * 반찬 이름 변경
     * @param banchanId
     * @param newName
     */
    @Transactional
    public void updateBanchanName(Long banchanId, String newName) {
        BanchanEntity banchan = banchanRepository.findById(banchanId).orElseThrow(() -> new IllegalArgumentException("입력값이 잘못되었습니다"));
        banchan.updateName(newName);
    }

    /**
     * 반찬 재고 변경
     * @param banchanId
     * @param newQuantity
     */
    @Transactional
    public void updateBanchanQuantity(Long banchanId, int newQuantity) {
        BanchanEntity banchan = banchanRepository.findById(banchanId).orElseThrow(() -> new IllegalArgumentException("입력값이 잘못되었습니다"));
        banchan.updateBanchanQuantity(newQuantity);
    }

    /**
     * 반찬 가격 변경
     * @param banchanId
     * @param newPrice
     */
    @Transactional
    public void updateBanchanPrice(Long banchanId, int newPrice) {
        BanchanEntity banchan = banchanRepository.findById(banchanId).orElseThrow(() -> new IllegalArgumentException("입력값이 잘못되었습니다"));
        banchan.updateBanchanPrice(newPrice);
    }

    /**
     * 반찬 및 반찬재료 등록
     * @param bancanDto
     */
    @Transactional
    public void saveBanchan(RequestBancanDto bancanDto) {
        BanchanEntity banchan = BanchanEntity.builder()
                .banchanName(bancanDto.getName())
                .banchanPrice(bancanDto.getPrice())
                .banchanStockQuantity(bancanDto.getQuantity())
                .createDate(bancanDto.getCreateDate())
                .expirationDate(bancanDto.getExpirationDate())
                .build();

        List<BanchanIngredientEntity> ingredientList = new ArrayList<>();
        for (RequestBanchanIngredienDto e : bancanDto.getIngredientList()) {
            ingredientList.add(
                    BanchanIngredientEntity.builder()
                            .ingredientName(e.getIngredientName())
                            .quantity(e.getIngredientQuantity())
                            .inputDate(e.getIngredientInputDate())
                            .expirationDate(e.getIngredientExpirationDate())
                            .build()
            );
        }

        banchanService.registBanchan(banchan, ingredientList);
    }
}
