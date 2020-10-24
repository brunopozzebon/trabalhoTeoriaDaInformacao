import api from './baseApi'

export async function encode(method:string, path:string, divisor:number){
    const data = (method==='goulomb') ? {path:path, divisor:divisor} : path;
    return await api.post(`/${method}/normal/encode`, data);
}

export async function decode(path:string){
    const response = await api.post(`/auto/decode`, path);
    return response;
}

export async function nextStep(){
    const response = await api.get(`/auto/nextStep`);
    return response.data;
}

export async function progress(){
    const response = await api.get(`/auto/progressPercentage`);
    return response.data;
}

export async function huffmanHashes(){
    const response = await api.get(`/huffman/hashes`);
    return response.data;
}

