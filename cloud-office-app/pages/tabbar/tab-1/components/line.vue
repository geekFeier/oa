<template>
	<view class="">
		<qiun-data-charts type="line" :opts="echartOpt1" :chartData="chartData1" background="none" />
	</view>
</template>

<script>
	import dayjs from '@/utils/dayjs';
	export default {
		data() {
			return {
				jingYingXData:[],
				profitData:[],
				incomeData:[],
				chartData1: {
					"categories": [
					],
					"series": []
				},
				echartOpt1: {
					"type": "line",
					"canvasId": "",
					"canvas2d": false,
					"background": "none",
					"animation": true,
					"timing": "easeOut",
					"duration": 1000,
					"color": [
						"#ff3047",
						"#47dfab",
					],
					"padding": [
						0,
						10,
						0,
						15
					],
					"rotate": false,
					"errorReload": true,
					"fontSize": 13,
					"fontColor": "#666666",
					"enableScroll": false,
					"touchMoveLimit": 60,
					"enableMarkLine": false,
					"dataLabel": false,
					"dataPointShape": true,
					"dataPointShapeType": "solid",
					"tapLegend": true,
					"xAxis": {
						"disabled": false,
						"axisLine": true,
						"axisLineColor": "#CCCCCC",
						"calibration": false,
						"fontColor": "#666666",
						"fontSize": 13,
						"rotateLabel": false,
						"itemCount": 5,
						"boundaryGap": "center",
						"disableGrid": true,
						"gridColor": "#CCCCCC",
						"gridType": "solid",
						"dashLength": 4,
						"gridEval": 1,
						"scrollShow": false,
						"scrollAlign": "left",
						"scrollColor": "#A6A6A6",
						"scrollBackgroundColor": "#EFEBEF",
						"format": ""
					},
					"yAxis": {
						"disabled": false,
						"disableGrid": false,
						"splitNumber": 5,
						"gridType": "dash",
						"dashLength": 2,
						"gridColor": "#CCCCCC",
						"padding": 10,
						"showTitle": false,
						"data": []
					},
					"legend": {
						"show": true,
						"position": "top",
						"float": "center",
						"padding": 5,
						"backgroundColor": "rgba(0,0,0,0)",
						"borderColor": "rgba(0,0,0,0)",
						"borderWidth": 0,
						"fontSize": 13,
						"fontColor": "#666666",
						"lineHeight": 11,
						"hiddenColor": "#CECECE",
						"itemGap": 10
					},
					"extra": {
						"line": {
							"type": "straight",
							"width": 2
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
						},
						"markLine": {
							"type": "solid",
							"dashLength": 4,
							"data": []
						}
					}
				},
				
			}
		},
		mounted() {
			this.getJingYingData();
		},
		methods:{
			getJingYingData() {
				this.$http("enterprise.dashboard.dashboard/enterprise_operate?years=" + dayjs().year() + "&month=" +
				dayjs().month() + 1, {}, "post").then(res => {
					// this.jingYingData = res.data.data;
					let data = res.data.data.months;
					for(let key in data){
						this.jingYingXData.push(key);
						this.incomeData.push(data[key].income);
						this.profitData.push(data[key].profit);
					}
					this.chartData1.categories = this.jingYingXData;
					this.chartData1.series = [{
							"name": "收入",
							"data": this.incomeData
						},
						{
							"name": "利润",
							"data": this.profitData
						}
				
					]
				})
			},	
		}
	}
</script>

<style>
</style>
