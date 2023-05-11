import missingLogo from '../images/missing-logo.png';
import { version } from '../../package.json';

/*
 * Constants
 */

const apiEndpoint = 'https://twitch-proxy.freecodecamp.rocks/twitch-api';
const channels = [
  'brunofin',
  'comster404',
  'cretetion',
  'freecodecamp',
  'habathcx',
  'noobs2ninjas',
  'OgamingSC2',
  'RobotCaleb',
];

/*
 * DOM Elements
 */

const searchTermElement = $('.js-search-term');
const statusFilterElements = $('.js-status-filter');
const channelsElement = $('.js-channels');
const versionElement = $('.js-version');

/*
 * Functions
 */

function makeTwitchRequest(type, id, success, error) {
  $.getJSON(`${apiEndpoint}/${type}/${id}`)
    .done(success)
    .fail(error);
}

function addToDOM(channelInformation) {
  channelsElement.prepend(
    `<a href="${channelInformation.href}" class="channel channel--${channelInformation.status} clearfix js-channel js-${channelInformation.status}" id="${channelInformation.id}">`
    + '<div class="col-xs-12 col-sm-2 text-center">'
    + `<img src="${channelInformation.logo}" class="channel__img img-circle" alt="${channelInformation.id} logo" title="${channelInformation.id}">`
    + '</div>'
    + '<div class="ellipsis col-sm-10">'
    + `<strong class="col-xs-12 col-sm-2 text-center">${channelInformation.id}</strong>`
    + '<div class="ellipsis col-xs-12 col-sm-9 col-sm-offset-1 text-center">'
    + `<span class="hidden-xs">${channelInformation.longDescription}</span>`
    + `<span class="hidden-sm hidden-md hidden-lg">${channelInformation.shortDescription}</span>`
    + '</div>'
    + '</div>'
    + '</a>',
  );
}

function addStreamInformation(channelInformation, res) {
  const channelAndStreamInformation = Object.assign({}, channelInformation);

  if (!res.stream) {
    channelAndStreamInformation.status = 'offline';
    channelAndStreamInformation.longDescription = 'Offline';
    channelAndStreamInformation.shortDescription = 'Offline';
  } else {
    channelAndStreamInformation.status = 'online';
    channelAndStreamInformation.longDescription = `${res.stream.channel.game}: ${res.stream.channel.status}`;
    channelAndStreamInformation.shortDescription = res.stream.channel.game;
  }

  return channelAndStreamInformation;
}

function handleGetStream(channelInformation) {
  return res => addToDOM(addStreamInformation(channelInformation, res));
}

function makeStreamRequest(channelInformation) {
  makeTwitchRequest(
    'streams',
    channelInformation.id,
    handleGetStream(channelInformation),
  );
}

function getOpenChannelInformation(channel, res) {
  return {
    id: channel,
    href: res.url,
    logo: res.logo || missingLogo,
  };
}

function handleGetChannelSuccess(channel) {
  return res => makeStreamRequest(getOpenChannelInformation(channel, res));
}

function getClosedChannelDescription(status) {
  if (status === 404) {
    return 'Channel does not exist';
  }
  if (status === 422) {
    return 'Channel is unavailable';
  }
  return 'Could not retrieve channel information';
}

function getClosedChannelInformation(channel, err) {
  const description = getClosedChannelDescription(err.status);

  return {
    id: channel,
    status: 'closed',
    href: '#',
    logo: missingLogo,
    longDescription: description,
    shortDescription: description,
  };
}

function handleGetChannelError(channel) {
  return err => addToDOM(getClosedChannelInformation(channel, err.responseJSON));
}

function makeChannelRequest(channel) {
  makeTwitchRequest(
    'channels',
    channel,
    handleGetChannelSuccess(channel),
    handleGetChannelError(channel),
  );
}

function applyStatusFilter(channelLinks) {
  const statusFilter = statusFilterElements.filter(':checked').val();

  if (statusFilter === 'online') {
    channelLinks.not('.js-online').hide();
  } else if (statusFilter === 'offline') {
    channelLinks.not('.js-offline, .js-closed').hide();
  }
}

function applySearchTermFilter(channelLinks) {
  const searchTerm = searchTermElement.val().toLowerCase();

  channelLinks.filter(':visible').each((_, e) => {
    if (!e.id.toLowerCase().includes(searchTerm)) {
      $(e).hide();
    }
  });
}

function applyFilters() {
  const channelLinks = $('.js-channel');
  channelLinks.show();
  applyStatusFilter(channelLinks);
  applySearchTermFilter(channelLinks);
}

/*
 * Initialise
 */

statusFilterElements.change(applyFilters);
searchTermElement.keyup(applyFilters);
channels.forEach(channel => makeChannelRequest(channel));
versionElement.text(version);
