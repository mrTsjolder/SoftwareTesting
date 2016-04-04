package at.archkb.server.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.PRECONDITION_FAILED, reason="Object was altered during editing.")
public class OptimisticLockingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Class<?> entityClass;
	
	private Serializable id;
	
	public OptimisticLockingException(Class<?> entityClass, Serializable id) {
		this.entityClass = entityClass;
		this.id = id;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public Serializable getId() {
		return id;
	}
}
