const steps = [
  {
    element: '#intro',
    popover: {
      title: 'Hamburger',
      description: 'Open && Close sidebar',
      position: 'left'
    }
  },
  {
    element: '#chartdiv',
    popover: {
      title: 'Breadcrumb',
      description: 'Indicate the current page location',
      position: 'bottom', 
    }
  },
  {
    element: '#navbar',
    popover: {
      title: 'Page Search',
      description: 'Page search, quick navigation',
      position: 'bottom'
    }
  },
  {
    element: '#searchbar',
    popover: {
      title: 'Screenfull',
      description: 'Set the page into fullscreen',
      position: 'bottom'
    }
  },
  {
    element: '#size-select',
    popover: {
      title: 'Switch Size',
      description: 'Switch the system size',
      position: 'left'
    }
  },
  {
    element: '#tags-view-container',
    popover: {
      title: 'Tags view',
      description: 'The history of the page you visited',
      position: 'bottom'
    },
    padding: 0
  }
]

export default steps
