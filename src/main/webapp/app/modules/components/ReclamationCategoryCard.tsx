import React from 'react';
import './ReclamationCategoryCard.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import Reclamationform from './Reclamationform';

const ReclamationCategoryCard = ({ categorydata }: any) => {
  return (
    <div className="réclamation-cards">
      <div className="logo">
        <FontAwesomeIcon icon={categorydata.icon} className="icon" />
      </div>
      <Link to={'/Reclamationform'}>
        <div className="title">{categorydata.name}</div>
      </Link>
    </div>
  );
};
export default ReclamationCategoryCard;
