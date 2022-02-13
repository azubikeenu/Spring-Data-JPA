package com.azubike.ellipsis.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OperationStatusModel {
	private String operationResult;
	private String operationName;

}