import React, { useEffect } from 'react';
import './Reclamationhistorycard.scss';
import axios from 'axios';
const Reclamationhistorycard = ({ complaintdata }: any) => {
  console.log(complaintdata[0]);
  //const newobject=Object.assign(complaintdata.citycitzenphoto)
  //console.log(newobject)
  const imageurl = 'data' + ':' + complaintdata.cityCitizenPhotos.imageContentType + ';' + complaintdata.cityCitizenPhotos.image;
  return (
    <>
      <div className="history-carte">
        <div className="reclamation-description">
          <div className="title">{complaintdata.complaintCategory.name}</div>
          <div className="state">{complaintdata.complaintstate}</div>
        </div>
        <div className="adresse">{complaintdata.address}</div>

        <div className="description">{complaintdata.description}</div>
        <div className="image">
          <img src={imageurl.toString()}></img>
        </div>
      </div>
    </>
  );
};

export default Reclamationhistorycard;
