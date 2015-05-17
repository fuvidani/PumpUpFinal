package sepm.ss15.grp16.service.exception;

import sepm.ss15.grp16.entity.DTO;

/**
 * Created by lukas on 02.05.2015.
 */
public class ValidationException extends ServiceException{

    private final String validationMessage;
    private final DTO validatedDTO;

    /**
     * Creates a ValidationException
     * @param validationMessage Message that should be displayed, after not valid input.
     */
    public ValidationException(String validationMessage) {
        super("Couldn't validate dto.");
        this.validationMessage = validationMessage;
        this.validatedDTO = null;
    }

    /**
     * Creates a ValidationException
     * @param validationMessage Message that should be displayed, after not valid input.
     * @param dto The dto which couldn't be validated.
     */
    public ValidationException(String validationMessage, DTO dto) {
        super("Couldn't validate dto: " + dto);
        this.validationMessage = validationMessage;
        this.validatedDTO = dto;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public DTO getValidatedDTO() {
        return validatedDTO;
    }
}
