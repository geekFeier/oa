<template>
	<view class="ringBox">
		<qiun-data-charts :opts="echartOpt" type="ring" :chartData="chartData" :errorShow="false" background="none"
			:tooltipShow="false" />
	</view>
</template>

<script>
	import dayjs from "@/utils/dayjs.min.js";
	export default {
		data() {
			return {
				count:"",
				chartData: {},
				echartOpt: {
					"type": "ring",
					"canvasId": "",
					"canvas2d": false,
					"background": "none",
					"animation": true,
					"timing": "easeOut",
					"duration": 1000,
					"color": [
						"#2070fd",
						"rgba(32,112,253,.3)"
					],
					// "padding": [
					// 	"85"
					// ],
					"rotate": false,
					"errorReload": true,
					"fontColor": "#666666",
					"enableScroll": false,
					"touchMoveLimit": 60,
					"enableMarkLine": false,
					"dataLabel": false,
					"dataPointShape": true,
					"dataPointShapeType": "solid",
					"tapLegend": false,
					"legend": {
						"show": false,
					},
					"title": {
						"name": "0",
						"fontSize": 16,
						"color": "#FF253D",
						"offsetX": 0,
						"offsetY": 0
					},
					"subtitle": {
						"name": "余额(元)",
						"fontSize": 16,
						"color": "#150E33",
						"offsetX": 0,
						"offsetY": 0
					},
					"extra": {
						"ring": {
							"ringWidth": 30,
							"centerColor": "#FFFFFF",
							"activeOpacity": 0.5,
							"activeRadius": 3,
							"offsetAngle": 0,
							"customRadius": 0,
							"labelWidth": 15,
							"border": true,
							"borderWidth": 3,
							"borderColor": "#FFFFFF",
							"linearType": "none"
						},
						"tooltip": {
							"showBox": true,
							"showArrow": true,
							"showCategory": false,
							"borderWidth": 0,
							"borderRadius": 0,
							"borderColor": "#000000",
							"borderOpacity": 0.7,
							"bgColor": "#000000",
							"bgOpacity": 0.7,
							"gridType": "solid",
							"dashLength": 4,
							"gridColor": "#CCCCCC",
							"fontColor": "#FFFFFF",
							"splitLine": true,
							"horizentalLine": false,
							"xAxisLabel": false,
							"yAxisLabel": false,
							"labelBgColor": "#FFFFFF",
							"labelBgOpacity": 0.7,
							"labelFontColor": "#666666"
						}
					}
				},
			}
		},
		mounted() {
			this.getData();
		},
		methods: {
			getData() {
				let params = {
					years: dayjs().year(),
					month: dayjs().month() + 1
				}
				this.$http("enterprise.dashboard.dashboard/Money", params, "get").then(res => {
					console.log(res, "sDAAAAAAAAA11111111111111");
					let data = res.data.data;
					this.count = data.borrow - data.loan;
					this.chartData = {
						"series": [{
							"data": [{
									"name": "借",
									"value": data.borrow
								},
								{
									"name": "贷",
									"value": data.loan
								},

							]
						}]
					}
					this.echartOpt.title.name= this.count.toString();
					this.$forceUpdate()
				})
			}
		}
	}
</script>

<style scoped>
	.ringBox {
		width: 300rpx;
		height: 300rpx;
	}
</style>
