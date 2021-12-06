package hello.itemservice.domain.item;

import lombok.Data;

/**
 * Bean Validation 구현체로 Hibernate Validator가 있다.
 * Range는 Hibernate Validator에서만 동작한다.
 *
 * 참고) javax.validation 으로 시작하면 특정 구현에 관계없이 제공되는 표준 인터페이스이고,
 * org.hibernate.validator 로 시작하면 하이버네이트 validator 구현체를 사용할 때만 제공되는 검증 기능이다. 실무에서 대부분 하이버네이트 validator를 사용하므로 자유롭게 사용해도 된다.
 */
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

/**
 * Bean Validation은 특정한 구현체가 아니라 Bean Validation 2.0(JSR-380)이라는 기술 표준이다.
 * 쉽게 이야기해서 검증 애노테이션과 여러 인터페이스의 모음이다.
 * NotBlnak, NotNull은 Bean Validation이 표준적으로 제공하는 기술이다.
 */
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Item {

    //    @NotNull(groups = UpdateCheck.class)
    private Long id;

    //    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

//    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
//    @Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

//    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
//    @Max(value = 9999, groups = {SaveCheck.class})
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
