package com.nagarro.everification.payload.request;

public record EventSourcePatchRequest(Long id, String outcome,
                                      String extension, String contactPerson, String customerCalledOn, String comment,
                                      String status) {
}
