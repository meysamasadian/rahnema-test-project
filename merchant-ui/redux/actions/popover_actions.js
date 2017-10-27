export const OPEN_POPOVER = 'OPEN_POPOVER';
export const CLOSE_POPOVER = 'CLOSE_POPOVER';

export const openPopover = (component) => {
    return {
        type: OPEN_POPOVER,
        component
    }
};

export const closePopover = () => {
    return {
        type: CLOSE_POPOVER,
    }
};