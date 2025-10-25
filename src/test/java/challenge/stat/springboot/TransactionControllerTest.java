package challenge.stat.springboot;

import challenge.stat.springboot.model.Transaction;
import challenge.stat.springboot.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class MockConfig{
        @Bean
        TransactionService transactionService(){
            return Mockito.mock(TransactionService.class);
        }
    }

    @Autowired
    private TransactionService transactionService;

    @Test
    @DisplayName("POST /transaction - 201 when accepted")
    void postTransactionAccepted() throws Exception{
        Mockito.doNothing().when(transactionService).addTransaction(any());

        String json = "{\"value\": 155.5, \"dateTime\": \"2025-10-22T08:30:59.122-03:00\"}";

        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /transaction - 400 bad request when invalid payload")
    void postTransactionWithInvalidPayload() throws Exception {
        String invalidJson = "{\"value\": \"not-number\", \"dateTime\": \"date-invalid\"}";

        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /transaction - with Future Date 422 Unprocessable Entity")
    void postTransactionWithFutureDate() throws Exception{
        Mockito.doNothing().when(transactionService).addTransaction(any());

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime futureDateTime = now.plusDays(1);
        double transactionValue = 150.5;
        Transaction transaction = new Transaction(transactionValue, futureDateTime);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("POST /transaction - with Negative Value 422 Unprocessable Entity")
    void postTransactionWithNegativaValue() throws Exception{
        Mockito.doNothing().when(transactionService).addTransaction(any());

        OffsetDateTime now = OffsetDateTime.now();
        double transactionValue = -150.5;
        Transaction transaction = new Transaction(transactionValue, now);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("DELETE /transaction - 201 when accepted")
    void deleteTransactionAccepted() throws Exception{
        Mockito.doNothing().when(transactionService).addTransaction(any());

        mockMvc.perform(delete("/transaction")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
