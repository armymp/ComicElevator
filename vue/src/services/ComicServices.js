import axios from 'axios';


export default {
  searchCollections(name) {
    return axios.get(`collectionslike/${name}`)
  },

  getAllCollections(user) {
    return axios.get(`collection/user/${user}`)
  },

  getCollectionById(collectionId) {
      return axios.get(`/collection/${collectionId}`)
  },

  addCollection(userId){
    return axios.post(`/collection/`, userId)
  },

  addComic(collectionid, comic){
    return axios.post(`/collection/${collectionid}/comic/`, comic)
  },

     updateCollection(collection){
        return axios.put(`/collection/${collection.collectionID}`, collection)
 }, 

  removeCollection(collectionId){
    return axios.delete(`/collection/${collectionId}`);
  },

  returnAllCollections(){
      return axios.get('/collection');
  },
  
  getAllUsernames() {
      return axios.get('/user');
  },
  getUserById(id) {
    return axios.get(`/user/id/${id}`);
},
  getUserByUsername(username) {
      return axios.get(`/user/${username}`);
  },

  getAllComicsByCollectionId(collectionId) {
      return axios.get(`/collection/${collectionId}`);
  },

  deleteComicByComicId(collectionID, comicID) {
      return axios.delete(`collection/${collectionID}/comic/${comicID}`)
  },
  getTopCharacterByUser(user){
    return axios.get(`/comic/${user}/topCharacters/`);
  },
  getTopCreatorByUser(user){
    return axios.get(`/comic/${user}/topCreator/`);
  },
  getAllTopCharacterByUser(){
    return axios.get(`/comic/topCharacters/`);
  },
  getAllTopCreatorByUser(){
    return axios.get(`/comic/topCreators/`);
  },
  getTopCharacterByCollection(id){
    return axios.get(`/collection/${id}/topCharacters/`);
  },
  getTopCreatorByCollection(id){
    return axios.get(`/collection/${id}/topCreators/`);
  },
  getFriendsByUserId(id) {
    return axios.get(`/friend/${id}`);
  },
  addFriendByUserId(friendDTO) {
    return axios.post(`/friend/`, friendDTO);
  },
  unfollowUser(id) {
    return axios.delete(`/friend/${id}`);
  }

//   getStats() {
//     return axios.get(`/stats/${type}`)


}
