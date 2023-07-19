package com.nagarro.everification.to;

import java.sql.Date;

public record EverificationTo(Long id,
                              String businessKey,
                              String priority,
                              String sourceBu,
                              String dcpReference,
                              String accountName,
                              String transactionCurrency,
                              Double transactionAmount,
                              String lockedBy,
                              Date createdOn) {
}
