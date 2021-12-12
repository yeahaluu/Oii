package com.ssafy.backend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("FiveLocPostReq")

public class FiveLocPostReq {
	@ApiModelProperty(name = "ù��° ��� ����")
	private float firstLatitude;
	@ApiModelProperty(name = "ù��° ��� �浵")
	private float firstLongitude;

	@ApiModelProperty(name = "�ι�° ��� ����")
	private float secondLatitude;
	@ApiModelProperty(name = "�ι�° ��� �浵")
	private float secondLongitude;

	@ApiModelProperty(name = "����° ��� ����")
	private float thirdLatitude;
	@ApiModelProperty(name = "����° ��� �浵")
	private float thirdLongitude;

	@ApiModelProperty(name = "�׹�° ��� ����")
	private float fourthLatitude;
	@ApiModelProperty(name = "�׹�° ��� �浵")
	private float fourthLongitude;

	@ApiModelProperty(name = "�ټ���° ��� ����")
	private float fifthLatitude;
	@ApiModelProperty(name = "�ټ���° ��� �浵")
	private float fifthLongitude;

}
