import React, { useState } from 'react';
import './ReclamationCategoryCard.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import Reclamationform from './Reclamationform';

const ReclamationCategoryCard = ({ categorydata }: any) => {
  const [selected, setSelected] = useState(false);
  const handleClick = () => {
    setSelected(!selected);
  };
  if (selected) {
    var complaintcatégory: string = `${categorydata.name}`;
  }

  return (
    <div className="réclamation-cards">
      <div className="logo">
        <FontAwesomeIcon icon={categorydata.icon} className="icon" />
      </div>
      <Link to={'/Reclamationform'}>
        <div className="title" onClick={handleClick}>
          {categorydata.name}
        </div>
      </Link>
    </div>
  );
};
export default ReclamationCategoryCard;
