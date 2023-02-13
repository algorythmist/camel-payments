package com.tecacet.payments;

import com.tecacet.payments.entity.PayeeProfileEntity;
import com.tecacet.payments.entity.PaymentEntity;
import com.tecacet.payments.repository.PaymentRepository;
import com.tecacet.payments.service.BalanceService;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CamelRoutesTest {

    @Autowired
    private TestData testData;
    @Autowired
    private ProducerTemplate template;

    @Autowired
    private PaymentRepository paymentRepository;

    @MockBean
    private BalanceService balanceService;

    @Test
    void testCustomers() {
        Mockito.when(balanceService.getBalance(Mockito.any(PayeeProfileEntity.class)))
                .thenReturn(BigDecimal.valueOf(10000));

        testData.createTestData(10, 5);
        template.sendBody("direct:customers", null);

        List<PaymentEntity> payments = paymentRepository.findAll();
        assertEquals(10, payments.size());

    }


}
