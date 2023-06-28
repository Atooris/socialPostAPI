package pl.atooris.SocialPostAPI.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Long id, Class<?> entity){
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' doesn't exist");
    }
}
