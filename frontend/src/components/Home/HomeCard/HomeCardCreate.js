import './homeCardStyle.css'

const HomeCardCreate = () => {

    return (
        <section className={'container'}>
            <div className="card">
                <div className={'box'}>
                    <div className={'content'}>
                        <i className="fas fa-hammer orange big"></i>
                        <h4 className={'title orange'}>Create</h4>
                        <p className={'orange bold'}>Create top joke and fun stories</p>
                    </div>
                </div>
            </div>
        </section>
    );
}

export default HomeCardCreate;
