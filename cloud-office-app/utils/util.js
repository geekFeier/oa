import md5 from 'md5';
export const calcSignature = (ticket, nonce_str, timestamp, url ) => {
  const str = `jsapi_ticket=${ticket}&nonce_str=${nonce_str}&timestamp=${timestamp}&url=${url}`;
  const sig = md5(str);
  return sig;
};