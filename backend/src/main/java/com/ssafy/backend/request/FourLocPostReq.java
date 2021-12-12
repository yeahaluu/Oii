package com.ssafy.backend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("FourLocPostReq")

public class FourLocPostReq {
	@ApiModelProperty(name = "ù��° ��� ����")
	float firstLatitude;
	@ApiModelProperty(name = "ù��° ��� �浵")
	float firstLongitude;

	@ApiModelProperty(name = "�ι�° ��� ����")
	float secondLatitude;
	@ApiModelProperty(name = "�ι�° ��� �浵")
	float secondLongitude;

	@ApiModelProperty(name = "����° ��� ����")
	float thirdLatitude;
	@ApiModelProperty(name = "����° ��� �浵")
	float thirdLongitude;

	@ApiModelProperty(name = "�׹�° ��� ����")
	float fourthLatitude;
	@ApiModelProperty(name = "�׹�° ��� �浵")
	float fourthLongitude;
}
