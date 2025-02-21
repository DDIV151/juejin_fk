export function getInfo(){
    const data=localStorage.getItem('xtjj_userdata');
    const dataObj=JSON.parse(data);
    const user_id=dataObj.user_id;
    const token=dataObj.token;
    return{
        user_id,
        token
    }
}