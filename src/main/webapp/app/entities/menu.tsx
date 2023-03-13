import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/city-service">
        <Translate contentKey="global.menu.entities.cityService" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/city-service-state">
        <Translate contentKey="global.menu.entities.cityServiceState" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-role">
        <Translate contentKey="global.menu.entities.userRole" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;