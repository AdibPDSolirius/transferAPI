package integration;

import static com.revolut.transfer.Application.PORT;
import static com.revolut.transfer.api.response.ResponseMessage.INSUFFICIENT_FUNDS;
import static com.revolut.transfer.api.response.ResponseMessage.RECEIVER_ACCOUNT_NOT_FOUND;
import static com.revolut.transfer.api.response.ResponseMessage.SENDER_ACCOUNT_NOT_FOUND;
import static com.revolut.transfer.api.response.ResponseMessage.TRANSFER_SUCCESSFUL;
import static com.revolut.transfer.api.response.ResponseStatus.FAILURE;
import static com.revolut.transfer.api.response.ResponseStatus.SUCCESS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;
import static spark.Spark.port;
import static spark.Spark.stop;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.gson.JsonObject;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.transfer.api.Router;
import com.revolut.transfer.data.AccountBalanceRepository;
import com.revolut.transfer.domain.AccountBalance;
import com.revolut.transfer.infrastructure.FilterModule;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class TransferIT {

    private static final String URI = "http://localhost:" + PORT + "/transfer";

    private static final BigInteger SENDER_ID = BigInteger.ZERO;
    private static final BigInteger RECEIVER_ID = BigInteger.ONE;
    private static final BigDecimal AMOUNT_IN_ACCOUNTS = BigDecimal.valueOf(100);

    private static final String STATUS_FIELD = "status";
    private static final String MESSAGE_FIELD = "message";
    private static final String APPLICATION_JSON = "application/json";

    private AccountBalanceRepository accountBalanceRepository;

    @Before
    public void setup() {
        port(PORT);

        final Injector injector = Guice.createInjector(new FilterModule());
        accountBalanceRepository = injector.getInstance(AccountBalanceRepository.class);

        initialiseDatabase(accountBalanceRepository);

        final Router router = injector.getInstance(Router.class);
        router.handleRequests();

        awaitInitialization();
    }

    private void initialiseDatabase(final AccountBalanceRepository accountBalanceRepository) {
        accountBalanceRepository.saveAccountBalance(new AccountBalance(SENDER_ID, AMOUNT_IN_ACCOUNTS));
        accountBalanceRepository.saveAccountBalance(new AccountBalance(RECEIVER_ID, AMOUNT_IN_ACCOUNTS));
    }

    @After
    public void tearDown() {
        stop();
        awaitStop();
    }

    @Test
    public void shouldReturn200SuccessfulTransferAndMoneyTransferredWhenValidPayload() {
        given().
                body(getRequestBody(SENDER_ID, RECEIVER_ID, AMOUNT_IN_ACCOUNTS))
                .with()
                .contentType(APPLICATION_JSON).
        when().
                put(URI).
        then().
                statusCode(HttpStatus.OK_200)
                .contentType(APPLICATION_JSON)
                .body(STATUS_FIELD, equalTo(SUCCESS.getStatus()))
                .and()
                .body(MESSAGE_FIELD, equalTo(TRANSFER_SUCCESSFUL.getMessage()));

        assertEquals(BigDecimal.ZERO, accountBalanceRepository.findAccountBalanceByID(SENDER_ID).getBalance());
        assertEquals(AMOUNT_IN_ACCOUNTS.add(AMOUNT_IN_ACCOUNTS), accountBalanceRepository.findAccountBalanceByID(RECEIVER_ID).getBalance());

    }

    @Test
    public void shouldReturn400BadRequestAndMoneyNotTransferredWhenInvalidPayload() {
        given().
                body(getRequestBody(SENDER_ID, BigInteger.valueOf(-1), AMOUNT_IN_ACCOUNTS))
                .with()
                .contentType(APPLICATION_JSON).
        when().
                put(URI).
        then().
                statusCode(HttpStatus.BAD_REQUEST_400);

        assertNoBalanceChange();

    }

    @Test
    public void shouldReturn409InsufficientFundsAndMoneyNotTransferredWhenInsufficientAmountInSenderAccount() {
        given().
                body(getRequestBody(SENDER_ID, RECEIVER_ID, AMOUNT_IN_ACCOUNTS.add(AMOUNT_IN_ACCOUNTS)))
                .with()
                .contentType(APPLICATION_JSON).
        when().
                put(URI).
        then().
                statusCode(HttpStatus.CONFLICT_409)
                .contentType(APPLICATION_JSON)
                .body(STATUS_FIELD, equalTo(FAILURE.getStatus()))
                .and()
                .body(MESSAGE_FIELD, equalTo(INSUFFICIENT_FUNDS.getMessage()));

        assertNoBalanceChange();

    }

    @Test
    public void shouldReturn422SenderAccountNotFoundAndMoneyNotTransferredWhenSenderAccountNotFound() {
        given().
                body(getRequestBody(BigInteger.TEN, RECEIVER_ID, AMOUNT_IN_ACCOUNTS))
                .with()
                .contentType(APPLICATION_JSON).
        when().
                put(URI).
        then().
                statusCode(HttpStatus.UNPROCESSABLE_ENTITY_422)
                .contentType(APPLICATION_JSON)
                .body(STATUS_FIELD, equalTo(FAILURE.getStatus()))
                .and()
                .body(MESSAGE_FIELD, equalTo(SENDER_ACCOUNT_NOT_FOUND.getMessage()));

        assertNoBalanceChange();
    }

    @Test
    public void shouldReturn422ReceiverAccountNotFoundAndMoneyNotTransferredWhenReceiverAccountNotFound() {
        given().
                body(getRequestBody(SENDER_ID, BigInteger.TEN, AMOUNT_IN_ACCOUNTS))
                .with()
                .contentType(APPLICATION_JSON).
        when().
                put(URI).
        then().
                statusCode(HttpStatus.UNPROCESSABLE_ENTITY_422)
                .contentType(APPLICATION_JSON)
                .body(STATUS_FIELD, equalTo(FAILURE.getStatus()))
                .and()
                .body(MESSAGE_FIELD, equalTo(RECEIVER_ACCOUNT_NOT_FOUND.getMessage()));

        assertNoBalanceChange();
    }

    private void assertNoBalanceChange() {
        assertEquals(AMOUNT_IN_ACCOUNTS, accountBalanceRepository.findAccountBalanceByID(SENDER_ID).getBalance());
        assertEquals(AMOUNT_IN_ACCOUNTS, accountBalanceRepository.findAccountBalanceByID(RECEIVER_ID).getBalance());
    }

    private String getRequestBody(final BigInteger senderId, final BigInteger receiverId, final BigDecimal amount) {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("senderID", senderId);
        jsonObject.addProperty("receiverID", receiverId);
        jsonObject.addProperty("amount", amount);

        return  jsonObject.toString();
    }
}
