package BusinessLayer;


/**
 * Aceasta interfata este folosita pentru a realiza validarea obiectelor ce vor fi introduse sau updatate
 * @param <T>
 * @author Zoli
 *
 */
public interface Validator<T> {

    public void validate(T t);
}
