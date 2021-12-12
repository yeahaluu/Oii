package com.ssafy.backend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("FiveLocPostReq")

public class FiveLocPostReq {
	@ApiModelProperty(name = "첫번째 장소 위도")
	private float firstLatitude;
	@ApiModelProperty(name = "첫번째 장소 경도")
	private float firstLongitude;

	@ApiModelProperty(name = "두번째 장소 위도")
	private float secondLatitude;
	@ApiModelProperty(name = "두번째 장소 경도")
	private float secondLongitude;

	@ApiModelProperty(name = "세번째 장소 위도")
	private float thirdLatitude;
	@ApiModelProperty(name = "세번째 장소 경도")
	private float thirdLongitude;

	@ApiModelProperty(name = "네번째 장소 위도")
	private float fourthLatitude;
	@ApiModelProperty(name = "네번째 장소 경도")
	private float fourthLongitude;

	@ApiModelProperty(name = "다섯번째 장소 위도")
	private float fifthLatitude;
	@ApiModelProperty(name = "다섯번째 장소 경도")
	private float fifthLongitude;

}
