import React from 'react';
import defaultPicture from '../assets/profile.png';
import { Link } from 'react-router-dom';
import ProfileImageWithDefault from './ProfileImageWithDefault';


const UserListItem = props => {
  const { user } = props;
  const { username, displayName, image } = user;

  return (
    <Link to={`/user/${username}`} className="list-group-item list-group-item-action">
<ProfileImageWithDefault className="rounded-circle" width="32" height="32" alt={`${username} profile`} image={image} />      <span className="ps-3">
        {displayName}@{username}
      </span>
    </Link>
  );
};

export default UserListItem;