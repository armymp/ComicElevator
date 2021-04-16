import axios from 'axios';


export default {
    getComicList(config){
        return axios.get("/marvel/",config);
    },
    getComicListTest(){
       let config = {
        params: {
            title : "Uncanny X-Men",
            issue : 1,
            page : 1
        }
        }
        return axios.get("/marvel/",config);
    }
}