const state = {
	token: uni.getStorageSync("token") || "",
	userInfo: uni.getStorageSync("userInfo") || {},
	enterprise: uni.getStorageSync("enterprise") || {},
	personType: uni.getStorageSync("personType") || "",
}
const getters = {

}

const actions = {
	GET_USER_INFO({
		commit
	}, data) {
		commit("SET_USER_INFO", data)
		commit("SET_enterprise", data.enterprise);
		if (data.is_admin == "admin") {
			commit("SET_PERSON_TYPE", 1)
		} else {
				commit("SET_PERSON_TYPE", data.jobs.job.flag)
		}

	},
	GET_TOKEN({
		commit
	}, data) {
		commit("SET_TOKEN", data.token)
	}
}

const mutations = {
	SET_enterprise(state, data) {
		uni.setStorageSync("enterprise", data);
		state.enterprise = data;
	},
	SET_USER_INFO(
		state, data) {
		uni.setStorageSync("userInfo", data);
		state.userInfo = data;
	},
	SET_PERSON_TYPE(
		state, data) {
		uni.setStorageSync("personType", data);
		state.personType = data;
	},
	SET_TOKEN(state, data) {
		uni.setStorageSync("token", data);
		state.token = data;
	}
}

export default {
	namespaced: true,
	state,
	actions,
	getters,
	mutations
}
