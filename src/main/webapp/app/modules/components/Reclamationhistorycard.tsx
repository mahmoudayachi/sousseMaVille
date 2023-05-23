import React, { useEffect, useState } from 'react';
import './Reclamationhistorycard.scss';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link, Navigate } from 'react-router-dom';
import Reclamationdetails from './Reclamationdetails';
import ReclamationCategoryCard from './ReclamationCategoryCard';

const Reclamationhistorycard = ({ complaintdata }: any) => {
  const imagecontent = complaintdata.cityCitizenPhotos[0].image;
  const imagetype = complaintdata.cityCitizenPhotos[0].imageContentType;
  const imageurl = 'data' + ':' + imagetype + ';' + 'base64,' + imagecontent;

  const deletereclamation = async () => {
    await axios
      .delete('http://localhost:8080/api/city-citizen-complaints/' + complaintdata.id)
      .then(response => {
        console.log(response);
        alert('réclamation supprimée');
      })
      .catch(error => console.log(error));
  };

  return (
    <>
      <div className="history-carte">
        <div className="reclamation-description">
          <div className="contenu-description">{complaintdata.complaintCategory.name}</div>
          <div className="state">{complaintdata.complaintstate}</div>
        </div>
        <div className="adresse">
          <FontAwesomeIcon icon={'location-dot'}></FontAwesomeIcon> {complaintdata.address}
          <div className="details">
            <Link to={'/Reclamationdetails'} state={complaintdata}>
              consulter
            </Link>
          </div>
        </div>

        <div className="supp-button-container">
          <button className="button-supp" onClick={deletereclamation}>
            <FontAwesomeIcon icon={'trash-can'}></FontAwesomeIcon>
          </button>
          <img className="reclamation-picture" src={imageurl.toString()}></img>
        </div>
      </div>
    </>
  );
};

export default Reclamationhistorycard;
