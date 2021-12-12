package com.ssafy.backend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("FourLocPostReq")

public class FourLocPostReq {
	@ApiModelProperty(name = "첫번째 장소 위도")
	float firstLatitude;
	@ApiModelProperty(name = "첫번째 장소 경도")
	float firstLongitude;

	@ApiModelProperty(name = "두번째 장소 위도")
	float secondLatitude;
	@ApiModelProperty(name = "두번째 장소 경도")
	float secondLongitude;

	@ApiModelProperty(name = "세번째 장소 위도")
	float thirdLatitude;
	@ApiModelProperty(name = "세번째 장소 경도")
	float thirdLongitude;

	@ApiModelProperty(name = "네번째 장소 위도")
	float fourthLatitude;
	@ApiModelProperty(name = "네번째 장소 경도")
	float fourthLongitude;
}
