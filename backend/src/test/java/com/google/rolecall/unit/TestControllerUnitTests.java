package com.google.rolecall.unit;

import com.google.rolecall.restcontrollers.TestController;
import com.google.rolecall.restcontrollers.RequestExceptions.UnsupportedOperationException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class TestControllerUnitTests {

  private TestController controller;

  @BeforeEach
  public void init() {
    controller = new TestController();
  }

  @Test
  public void paramGetValueReturn_success() throws Exception {
    //Execute
    String response = controller.sayHello("Jared").get();

    // Assert
    assert(response).equals("Hello Jared");
  }

  @Test
  public void postInvokeError_failure() throws Exception {
    //Execute
    UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, controller::throwException);

    // Assert
    assert(ex.getMessage()).equals("POST is not defined for this endpoint");
  }
}
