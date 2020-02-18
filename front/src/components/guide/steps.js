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
      position: 'bottom',
      offset: 750
    }
  },
  {
    element: '#sidenav',
    popover: {
      title: 'Screenfull',
      description: 'Set the page into fullscreen',
      position: 'left'
    }
  },
  {
    element: '#newstab',
    popover: {
      title: 'Switch Size',
      description: 'Switch the system size',
      position: 'top'
    },
    onNext: () => {
    },
  },
  {
    element: '#chatting',
    opacity: 0,
    popover: {
      title: 'Tags view',
      description: 'The history of the page you visited',
      position: 'top'
    },
  }
]

export default steps
