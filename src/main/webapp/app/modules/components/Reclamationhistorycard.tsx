import React, { useEffect, useState } from 'react';
import './Reclamationhistorycard.scss';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
const Reclamationhistorycard = ({ complaintdata }: any) => {
  console.log(complaintdata.cityCitizenPhotos[0]);
  const imagecontent = complaintdata.cityCitizenPhotos[0].image;
  const imagetype = complaintdata.cityCitizenPhotos[0].imageContentType;

  const imageurl = 'data' + ':' + imagetype + ';' + 'base64,' + imagecontent;

  const deletereclamation = () => {
    axios
      .delete('http://localhost:8080/api/city-citizen-complaints/' + complaintdata.id)
      .then(response => console.log(response))
      .catch(error => console.log(error));
  };

  return (
    <>
      <div className="history-carte">
        <div className="reclamation-description">
          <div className="contenu-description">{complaintdata.description}</div>
          <div className="state">{complaintdata.complaintstate}</div>
        </div>
        <div className="adresse">
          <FontAwesomeIcon icon={'location-dot'}></FontAwesomeIcon> {complaintdata.address}
        </div>

        <div className="image">
          <img className="reclamation-picture" src={imageurl.toString()}></img>
        </div>
        <div className="trash-container">
          <button onClick={deletereclamation} className="delete-button">
            <FontAwesomeIcon icon={'trash-can'}></FontAwesomeIcon>
          </button>
        </div>
      </div>
    </>
  );
};

export default Reclamationhistorycard;
