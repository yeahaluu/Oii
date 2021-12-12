package com.ssafy.backend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("TwoLocPostReq")

public class TwoLocPostReq {
	@ApiModelProperty(name = "첫번째 장소 위도")
	float firstLatitude;
	@ApiModelProperty(name = "첫번째 장소 경도")
	float firstLongitude;

	@ApiModelProperty(name = "두번째 장소 위도")
	float secondLatitude;
	@ApiModelProperty(name = "두번째 장소 경도")
	float secondLongitude;
}
