package lv.javaguru.currencyConverter.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter@Setter
public class ApplicationState {
private boolean connecting;
}
