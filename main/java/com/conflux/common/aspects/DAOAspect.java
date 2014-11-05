package com.conflux.common.aspects;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.conflux.common.ApplicationBaseException;

@Aspect
@Component
public class DAOAspect {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DAOAspect.class);

	/**
	 * Applies around advice for logging and exception handling of all service
	 * methods (under package/subpackage of com.conflux.service)
	 * 
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* com.conflux.dal.dao.*.*(..))")
	public Object handleCrossCuttingConcerns(
			ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		/** Log method signature and Input parameters if debugging is enabled **/
		if (LOGGER.isDebugEnabled()) {
			int i = 1;
			Object[] arguments = proceedingJoinPoint.getArgs();
			String argumentList = "";
			if (null != arguments && arguments.length > 0) {
				for (Object argument : arguments) {
					argumentList = "\n" + argumentList + "Argument " + i + "="
							+ argument.toString();
				}
				LOGGER.debug("Entering Method "
						+ proceedingJoinPoint.getSignature().toLongString()
						+ " of " + proceedingJoinPoint.getTarget().getClass()
						+ ", arguments passed are " + argumentList);
			} else {
				LOGGER.debug("Entering Method "
						+ proceedingJoinPoint.getSignature().toLongString()
						+ " (no arguments passed) " + " of  "
						+ proceedingJoinPoint.getTarget().getClass());
			}
		}
		try {
			Object retVal = proceedingJoinPoint.proceed();
			return retVal;
		} catch (Exception e) {
			/** Re-throw if already an instance of ApplicationBaseException **/
			if (e instanceof ApplicationBaseException) {
				throw (ApplicationBaseException) e;
			}
			LOGGER.error("Exception occured in the DAO Layer "
					+ proceedingJoinPoint.getTarget().getClass()
					+ "in the method with Signature "
					+ proceedingJoinPoint.getSignature().toLongString(), e);
			throw new ApplicationBaseException(
					"Exception occured in the DAO Layer "
							+ proceedingJoinPoint.getTarget().getClass()
							+ "in the method with Signature "
							+ proceedingJoinPoint.getSignature()
									.toShortString(), e);
		}
	}
}
