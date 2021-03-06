package tBackend.lib.annotations

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.Target

import static java.lang.annotation.RetentionPolicy.RUNTIME

@Retention(RUNTIME)
@Target([ElementType.METHOD, ElementType.FIELD])
@interface ExcludeFromSerialize {

}
