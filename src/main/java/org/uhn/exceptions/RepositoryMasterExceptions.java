package org.uhn.exceptions;

@SuppressWarnings("serial")
public class RepositoryMasterExceptions extends RuntimeException {
	public RepositoryMasterExceptions(int messageId) throws Exception {
		switch(messageId) {
		case ExceptionList.REP00001 :
			throw new RepositoryMasterExceptions(ExceptionMessages.REPM00001);
		case ExceptionList.REP00002 :
			throw new RepositoryMasterExceptions(ExceptionMessages.REPM00002);
		case ExceptionList.REP00003 :
			throw new RepositoryMasterExceptions(ExceptionMessages.REPM00003);
		case ExceptionList.REP00004 :
			throw new RepositoryMasterExceptions(ExceptionMessages.REPM00004);
		case ExceptionList.REP00005 :
			throw new RepositoryMasterExceptions(ExceptionMessages.REPM00005);
		case ExceptionList.REP00006 :
			throw new RepositoryMasterExceptions(ExceptionMessages.REPM00006);
		case ExceptionList.REP00007 :
			throw new RepositoryMasterExceptions(ExceptionMessages.REPM00007);
		case ExceptionList.REP00008 :
			throw new RepositoryMasterExceptions(ExceptionMessages.REPM00008);
		}
	}
	
	public RepositoryMasterExceptions(String exceptionMessage) {
		super(exceptionMessage);
	}
}
