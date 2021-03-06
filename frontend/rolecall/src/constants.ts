import { AppTypes } from 'src/types';

/**
 * The nav bar data structures to build out the
 * navigation panels that link to other views
 */
export let constNavBarEntries: AppTypes.NavBarChild[] = [
  {
    name: 'Dashboard',
    routerLinkUrl: '/dashboard',
    icon: 'dashboard'
  },
  // {
  //   name: 'Settings',
  //   routerLinkUrl: 'settings/',
  //   icon: 'settings'
  // },
  {
    name: 'Users',
    routerLinkUrl: 'user/',
    icon: 'person'
  },
  {
    name: 'Segments',
    routerLinkUrl: 'segment/',
    icon: 'extension'
  },
  {
    name: 'Casts',
    routerLinkUrl: 'cast/',
    icon: 'group'
  },
  {
    name: 'Performances',
    routerLinkUrl: 'performance/',
    icon: 'sports_kabaddi'
  },
  {
    name: 'Unavailabilities',
    routerLinkUrl: 'unavailability',
    icon: 'report_problem'
  }
];

/** Color variables */
export let Colors = {
  white: 'white',
  offWhite: '#f4f4f4',
  offoffWhite: '#d9d9d9',
  AAOrange: '#ffae00'
}