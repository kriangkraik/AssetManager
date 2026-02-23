package org.example.assetmanager.asset.exceptions;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String entity, Object id) {
        super("ENTITY_NOT_FOUND", entity + " not found with id: " + id);
    }

}
